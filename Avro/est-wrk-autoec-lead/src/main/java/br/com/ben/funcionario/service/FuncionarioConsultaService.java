package br.com.mac.funcionario.service;

import br.com.mac.funcionario.blue.domain.FuncionarioVM;
import br.com.mac.funcionario.dto.IEmpresaDTO;
import br.com.mac.funcionario.dto.request.FuncionarioStatusRequestDTO;
import br.com.mac.funcionario.dto.request.RequestDTO;
import br.com.mac.funcionario.dto.response.FuncionarioResponse;
import br.com.mac.funcionario.dto.response.FuncionarioStatusResponseDTO;
import br.com.mac.funcionario.dto.response.PageFuncionarioResponse;
import br.com.mac.funcionario.dto.response.ResponseDTO;
import br.com.mac.funcionario.enums.Acao;
import br.com.mac.funcionario.repository.blue.CustomQueryRepositoryImpl;
import br.com.mac.funcionario.repository.blue.EmpresaRepository;
import br.com.mac.funcionario.repository.blue.FuncionarioProdutoRepository;
import br.com.mac.funcionario.repository.blue.FuncionarioRepository;
import br.com.mac.funcionario.repository.blue.projection.FuncionarioProdutoProjection;
import br.com.mac.funcionario.service.mapper.FuncionarioProdutoResponseMapper;
import br.com.mac.funcionario.service.mapper.FuncionarioResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioConsultaService {

  private static final Logger logger = LoggerFactory.getLogger(FuncionarioConsultaService.class);

  @Autowired private CustomQueryRepositoryImpl queryRepositoryImpl;

  @Autowired private EmpresaRepository empresaRepository;

  @Autowired private FuncionarioRepository funcionarioRepository;

  @Autowired private FuncionarioProdutoRepository funcionarioProdutoRepository;

  @Autowired private FuncionarioResponseMapper funcionarioResponseMapper;

  @Autowired private FuncionarioProdutoResponseMapper funcionarioProdutoResponseMapper;

  @PersistenceContext(unitName = "blue")
  private EntityManager entityManager;

  public ResponseEntity<ResponseDTO> consultarStatusFuncionarios(
      final RequestDTO requestDto, BindingResult result) {

    Optional<IEmpresaDTO> iEmpresaDTO =
        empresaRepository.findIEmpresaDTOByCnpj(requestDto.getCnpj());
    if (iEmpresaDTO.isEmpty()) {
      result.addError(new ObjectError("empresa", "CNPJ não encontrado: " + requestDto.getCnpj()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(ResponseDTO.builder().cnpj(requestDto.getCnpj()).funcionarios(null).build());
    }

    Collection<FuncionarioStatusResponseDTO> funcionarios = new ArrayList<>();

    String cpf = null;
    try {
      StringBuilder builder = new StringBuilder();

      LinkedHashMap<String, br.com.mac.funcionario.dto.FuncionarioDTO> funcionarioDTOS =
          new LinkedHashMap<>();

      for (FuncionarioStatusRequestDTO f : requestDto.getFuncionarios()) {

        if (!f.getCpf().isEmpty()) {
          builder.append(",");
        }
        builder.append("'").append(f.getCpf()).append("'");

        if (!funcionarioDTOS.containsKey(f.getCpf())) {
          funcionarioDTOS.put(f.getCpf(), null);
        }
      }

      LinkedHashMap<String, br.com.mac.funcionario.dto.FuncionarioDTO> link =
          queryRepositoryImpl.getFuncionarioDTOByCpfAndCnpj(
              builder, requestDto.getCnpj(), funcionarioDTOS);

      for (Map.Entry<String, br.com.mac.funcionario.dto.FuncionarioDTO> current : link.entrySet()) {
        funcionarios.add(
            FuncionarioStatusResponseDTO.builder()
                .cpf(current.getValue() != null ? current.getValue().getCpf() : current.getKey())
                .status(
                    Acao.toEnum(current.getValue() != null ? current.getValue().getStatus() : 3L)
                        .getClienteStatus())
                .build());
      }

    } catch (NoResultException e) {
      result.addError(new ObjectError("motivo", "NÃO ENCONTRADO"));
      logger.error("Erro ao consultar status do funcionário - CPF: {} - NÃO ENCONTRADO", cpf);

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              ResponseDTO.builder()
                  .funcionarios(
                      Collections.singletonList(
                          FuncionarioStatusResponseDTO.builder().cpf(cpf).build()))
                  .build());
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseDTO.builder().funcionarios(funcionarios).build());
  }

  @Transactional(
      isolation = Isolation.READ_UNCOMMITTED,
      transactionManager = "transactionManagerBlue")
  public PageFuncionarioResponse listar(
      @Valid String cpf,
      @Valid LocalDate dataNascimento,
      @Valid List<Long> idConta,
      @Valid Long idEmpresa,
      @Valid Long idPessoa,
      @Valid Long idSetor,
      @Valid Long idSubgrupo,
      @Valid String matricula,
      @Valid String nome,
      @Valid String numeroIdentidade,
      @Valid String orderBy,
      @Valid Integer page,
      @Valid String sexo,
      @Valid Integer size,
      @Valid List<Long> status) {

    var query =
        getQuery(
            cpf,
            dataNascimento,
            idConta,
            idEmpresa,
            idPessoa,
            idSetor,
            idSubgrupo,
            matricula,
            nome,
            numeroIdentidade,
            orderBy,
            page,
            sexo,
            size,
            status);

    Page<FuncionarioVM> allFuncionario =
        PageableExecutionUtils.getPage(
            query.getResultList(),
            PageRequest.of(page, size),
            () -> {
              var countQuery =
                  getCountQuery(
                      cpf,
                      dataNascimento,
                      idConta,
                      idEmpresa,
                      idPessoa,
                      idSetor,
                      idSubgrupo,
                      matricula,
                      nome,
                      numeroIdentidade,
                      sexo,
                      status);
              Assert.notNull(countQuery, "TypedQuery must not be null!");
              return (Integer) countQuery.getSingleResult();
            });

    List<FuncionarioResponse> funcionarioResponses =
        allFuncionario.stream()
            .map(
                funcionario -> {
                  var funcionarioResponse = funcionarioResponseMapper.toResponse(funcionario);
                  adicionarFuncionarioProduto(funcionario, funcionarioResponse);
                  return funcionarioResponse;
                })
            .collect(Collectors.toList());

    Page<FuncionarioResponse> pageResponse =
        new PageImpl<>(
            funcionarioResponses, allFuncionario.getPageable(), allFuncionario.getTotalElements());

    return PageFuncionarioResponse.builder()
        .content(pageResponse.getContent())
        .hasContent(pageResponse.hasContent())
        .first(pageResponse.hasContent())
        .last(pageResponse.hasContent())
        .nextPage(pageResponse.nextOrLastPageable().getPageNumber())
        .number(pageResponse.getNumber())
        .numberOfElements(pageResponse.getNumberOfElements())
        .previousPage(pageResponse.previousOrFirstPageable().getPageNumber())
        .size(pageResponse.getSize())
        .totalElements(pageResponse.getTotalElements())
        .totalPages(pageResponse.getTotalPages())
        .totalElements(pageResponse.getTotalElements())
        .build();
  }

  private Query getCountQuery(
      String cpf,
      LocalDate dataNascimento,
      List<Long> idConta,
      Long idEmpresa,
      Long idPessoa,
      Long idSetor,
      Long idSubgrupo,
      String matricula,
      String nome,
      String numeroIdentidade,
      String sexo,
      List<Long> status) {
    var sb = new StringBuilder();
    sb.append("select count(distinct funcionari0_.Id_Funcionario) from Funcionarios funcionari0_ ");
    sb.append(
        "inner join Enderecos endereco1_ (nolock) on funcionari0_.Id_EnderecoEntrega = endereco1_.Id_Endereco ");
    sb.append(
        "inner join FuncionariosProdutos funcionari2_ (nolock) on funcionari0_.Id_Funcionario = funcionari2_.Id_Funcionario ");
    sb.append("inner join Contas conta3_ (nolock) on funcionari2_.Id_Conta = conta3_.Id_Conta ");
    sb.append(
        "inner join Pessoas pessoa4_ (nolock) on funcionari0_.Id_PessoaFisica = pessoa4_.Id_PessoaFisica ");
    sb.append(
        "inner join Empresas empresa5_ (nolock) on funcionari0_.Id_Empresa = empresa5_.Id_Empresa ");
    sb.append(
        "inner join EMPRESASCARGASDETALHES empresacar6_ (nolock) on funcionari0_.Id_Funcionario = empresacar6_.Id_Funcionario ");
    sb.append(
        "inner join EMPRESASCARGAS empresacar7_ (nolock) on empresacar6_.Id_EmpresaCarga = empresacar7_.ID_EMPRESACARGA ");
    sb.append(
        "inner join CARGASmacEFICIOS cargamacef8_ (nolock) on empresacar7_.Id_Cargamaceficio = cargamacef8_.ID_CARGAmacEFICIO ");
    sb.append(
        "inner join ARQUIVOCARGASTD arquivocar9_ (nolock) on cargamacef8_.ID_ARQUIVOCARGASTD = arquivocar9_.ID_ARQUIVOCARGASTD ");
    sb.append("where funcionari0_.STATUS is not null ");

    if (!ObjectUtils.isEmpty(idConta)) {
      sb.append("and conta3_.Id_Conta in (:idConta) ");
    }

    if (!ObjectUtils.isEmpty(idEmpresa)) {
      sb.append("and empresa5_.ID_EMPRESA = :idEmpresa ");
    }

    if (!ObjectUtils.isEmpty(status)) {
      sb.append("and funcionari0_.STATUS in (:funcionarioStatus) ");
    }

    if (!ObjectUtils.isEmpty(cpf)) {
      sb.append("and upper(pessoa4_.CPF) like :cpf ");
    }

    if (!ObjectUtils.isEmpty(dataNascimento)) {
      sb.append("and pessoa4_.DATANASCIMENTO = :dataNascimento ");
    }

    if (!ObjectUtils.isEmpty(idSetor)) {
      sb.append("and funcionari0_.ID_EmpresaSetor = :idSetor ");
    }

    if (!ObjectUtils.isEmpty(idSubgrupo)) {
      sb.append("and funcionari0_.STATUS in (:funcionarioStatus) ");
    }

    if (!ObjectUtils.isEmpty(idPessoa)) {
      sb.append("and pessoa4_.ID_PESSOAFISICA = :idPessoa ");
    }

    if (!ObjectUtils.isEmpty(matricula)) {
      sb.append("and upper(funcionari0_.MATRICULA) like :matricula ");
    }

    if (!ObjectUtils.isEmpty(nome)) {
      sb.append("and pessoa4_.NOME like :nome ");
    }

    if (!ObjectUtils.isEmpty(numeroIdentidade)) {
      sb.append("and upper(pessoa4_.NUMEROIDENTIDADE) like :numeroIdentidade ");
    }

    if (!ObjectUtils.isEmpty(sexo)) {
      sb.append("and pessoa4_.SEXO = :sexo ");
    }

    sb.append("and arquivocar9_.STATUS = 3 ");
    sb.append("and cargamacef8_.STATUS <> 0");

    var countQuery = entityManager.createNativeQuery(sb.toString());

    setParameters(
        cpf,
        dataNascimento,
        idConta,
        idEmpresa,
        idPessoa,
        idSetor,
        idSubgrupo,
        matricula,
        nome,
        numeroIdentidade,
        sexo,
        status,
        countQuery);

    return countQuery;
  }

  private Query getQuery(
      @Valid String cpf,
      @Valid LocalDate dataNascimento,
      @Valid List<Long> idConta,
      @Valid Long idEmpresa,
      @Valid Long idPessoa,
      @Valid Long idSetor,
      @Valid Long idSubgrupo,
      @Valid String matricula,
      @Valid String nome,
      @Valid String numeroIdentidade,
      @Valid String orderBy,
      @Valid Integer page,
      @Valid String sexo,
      @Valid Integer size,
      @Valid List<Long> status) {
    StringBuilder sb = new StringBuilder();
    sb.append("select distinct ");
    sb.append("funcionari0_.ID_FUNCIONARIO as funcionarioId, ");
    sb.append("pessoa4_.ID_PESSOAFISICA as pessoaId, ");
    sb.append("empresa5_.ID_EMPRESA as empresaId, ");
    sb.append("funcionari0_.DataCadastro as funcionarioDataCadastro, ");
    sb.append("funcionari0_.StatusData as funcionarioDataStatus, ");
    sb.append("funcionari0_.ID_EmpresaSetor as funcionarioEmpresaSetorId, ");
    sb.append("funcionari0_.MATRICULA as funcionarioMatricula, ");
    sb.append("funcionari0_.STATUS as funcionarioStatus, ");
    sb.append("pessoa10_.CPF as empresaCnpj, ");
    sb.append("pessoa4_.CPF as pessoaCpf, ");
    sb.append("pessoa4_.DATANASCIMENTO as pessoaDataNascimento, ");
    sb.append("pessoa4_.ID_EMISSOR as pessoaEmissorId, ");
    sb.append("pessoa4_.NOME as pessoaNome, ");
    sb.append("pessoa4_.NUMEROIDENTIDADE as pessoaNumeroIdentidade, ");
    sb.append("pessoa4_.SEXO as pessoaSexo, ");
    sb.append("endereco1_.BAIRRO as enderecoBairro, ");
    sb.append("endereco1_.CEP as enderecoCep, ");
    sb.append("endereco1_.CIDADE as enderecoCidade, ");
    sb.append("endereco1_.COMPLEMENTOENDERECO as enderecoComplemento, ");
    sb.append("endereco1_.DATAULTIMAATUALIZACAO as enderecoDataUltimaAtualizacao, ");
    sb.append("endereco1_.DATAINCLUSAO as enderecoDataInclusao, ");
    sb.append("endereco1_.NOMELOGRADOURO as enderecoLogradouro, ");
    sb.append("endereco1_.NUMEROENDERECO as enderecoNumero, ");
    sb.append("endereco1_.UF as enderecoUf ");
    sb.append("from Funcionarios funcionari0_ ");
    sb.append(
        "inner join Enderecos endereco1_ (nolock) on funcionari0_.Id_EnderecoEntrega = endereco1_.Id_Endereco ");
    sb.append(
        "inner join FuncionariosProdutos funcionari2_ (nolock) on funcionari0_.Id_Funcionario = funcionari2_.Id_Funcionario ");
    sb.append("inner join Contas conta3_ (nolock) on funcionari2_.Id_Conta = conta3_.Id_Conta ");
    sb.append(
        "inner join Pessoas pessoa4_ (nolock) on funcionari0_.Id_PessoaFisica = pessoa4_.Id_PessoaFisica ");
    sb.append(
        "inner join Empresas empresa5_ (nolock) on funcionari0_.Id_Empresa = empresa5_.Id_Empresa ");
    sb.append(
        "inner join EMPRESASCARGASDETALHES empresacar6_ (nolock) on funcionari0_.Id_Funcionario = empresacar6_.Id_Funcionario ");
    sb.append(
        "inner join EMPRESASCARGAS empresacar7_ (nolock) on empresacar6_.Id_EmpresaCarga = empresacar7_.ID_EMPRESACARGA ");
    sb.append(
        "inner join CARGASmacEFICIOS cargamacef8_ (nolock) on empresacar7_.Id_Cargamaceficio = cargamacef8_.ID_CARGAmacEFICIO ");
    sb.append(
        "inner join ARQUIVOCARGASTD arquivocar9_ (nolock) on cargamacef8_.ID_ARQUIVOCARGASTD = arquivocar9_.ID_ARQUIVOCARGASTD ");
    sb.append(
        "inner join Pessoas pessoa10_ (nolock) on empresa5_.Id_Pessoa = pessoa10_.Id_PessoaFisica ");
    sb.append("where funcionari0_.STATUS is not null ");

    if (!ObjectUtils.isEmpty(idConta)) {
      sb.append("and conta3_.Id_Conta in (:idConta) ");
    }

    if (!ObjectUtils.isEmpty(idEmpresa)) {
      sb.append("and empresa5_.ID_EMPRESA = :idEmpresa ");
    }

    if (!ObjectUtils.isEmpty(status)) {
      sb.append("and funcionari0_.STATUS in (:funcionarioStatus) ");
    }

    if (!ObjectUtils.isEmpty(cpf)) {
      sb.append("and upper(pessoa4_.CPF) like :cpf ");
    }

    if (!ObjectUtils.isEmpty(dataNascimento)) {
      sb.append("and pessoa4_.DATANASCIMENTO = :dataNascimento ");
    }

    if (!ObjectUtils.isEmpty(idSetor)) {
      sb.append("and funcionari0_.ID_EmpresaSetor = :idSetor ");
    }

    if (!ObjectUtils.isEmpty(idSubgrupo)) {
      sb.append("and funcionari0_.STATUS in (:funcionarioStatus) ");
    }

    if (!ObjectUtils.isEmpty(idPessoa)) {
      sb.append("and pessoa4_.ID_PESSOAFISICA = :idPessoa ");
    }

    if (!ObjectUtils.isEmpty(matricula)) {
      sb.append("and upper(funcionari0_.MATRICULA) like :matricula ");
    }

    if (!ObjectUtils.isEmpty(nome)) {
      sb.append("and pessoa4_.NOME like :nome ");
    }

    if (!ObjectUtils.isEmpty(numeroIdentidade)) {
      sb.append("and upper(pessoa4_.NUMEROIDENTIDADE) like :numeroIdentidade ");
    }

    if (!ObjectUtils.isEmpty(sexo)) {
      sb.append("and pessoa4_.SEXO = :sexo ");
    }

    sb.append("and arquivocar9_.STATUS = 3 ");
    sb.append("and cargamacef8_.STATUS <> 0 ");
    sb.append("order by pessoa4_.NOME ");
    sb.append(orderBy);
    sb.append(", funcionari0_.Id_Funcionario ");
    sb.append("offset :page rows fetch next :size rows only");

    var nativeQuery = entityManager.createNativeQuery(sb.toString(), FuncionarioVM.class);

    setParameters(
        cpf,
        dataNascimento,
        idConta,
        idEmpresa,
        idPessoa,
        idSetor,
        idSubgrupo,
        matricula,
        nome,
        numeroIdentidade,
        sexo,
        status,
        nativeQuery);

    nativeQuery.setParameter("page", page * size);
    nativeQuery.setParameter("size", size);

    return nativeQuery;
  }

  private void setParameters(
      String cpf,
      LocalDate dataNascimento,
      List<Long> idConta,
      Long idEmpresa,
      Long idPessoa,
      Long idSetor,
      Long idSubgrupo,
      String matricula,
      String nome,
      String numeroIdentidade,
      String sexo,
      List<Long> status,
      Query query) {
    if (!ObjectUtils.isEmpty(idConta)) {
      query.setParameter("idConta", idConta);
    }

    if (!ObjectUtils.isEmpty(idEmpresa)) {
      query.setParameter("idEmpresa", idEmpresa);
    }

    if (!ObjectUtils.isEmpty(status)) {
      query.setParameter("funcionarioStatus", status);
    }

    if (!ObjectUtils.isEmpty(cpf)) {
      query.setParameter("cpf", wrapLikeQuery(cpf));
    }

    if (!ObjectUtils.isEmpty(dataNascimento)) {
      query.setParameter("dataNascimento", dataNascimento);
    }

    if (!ObjectUtils.isEmpty(idSetor)) {
      query.setParameter("idSetor", idSetor);
    }

    if (!ObjectUtils.isEmpty(idSubgrupo)) {
      query.setParameter("idSubgrupo", idSubgrupo);
    }

    if (!ObjectUtils.isEmpty(idPessoa)) {
      query.setParameter("idPessoa", idPessoa);
    }

    if (!ObjectUtils.isEmpty(matricula)) {
      query.setParameter("matricula", wrapLikeQuery(matricula));
    }

    if (!ObjectUtils.isEmpty(nome)) {
      query.setParameter("nome", wrapLikeQuery(nome));
    }

    if (!ObjectUtils.isEmpty(numeroIdentidade)) {
      query.setParameter("numeroIdentidade", wrapLikeQuery(numeroIdentidade));
    }

    if (!ObjectUtils.isEmpty(sexo)) {
      query.setParameter("sexo", sexo);
    }
  }

  private void adicionarFuncionarioProduto(
          FuncionarioVM funcionario, FuncionarioResponse funcionarioResponse) {
    List<FuncionarioProdutoProjection> funcionarioProdutos =
        funcionarioRepository.findAllFuncionarioProduto(funcionario.getFuncionarioId());
    funcionarioResponse.setFuncionariosProdutos(
        funcionarioProdutoResponseMapper.toResponse(new HashSet<>(funcionarioProdutos)));
  }

  protected String wrapLikeQuery(String txt) {
    return "%" + txt.toUpperCase(Locale.getDefault()) + '%';
  }

  public Integer quantidadeFuncionarios(
      @Valid String cpf,
      @Valid LocalDate dataNascimento,
      @Valid List<Long> idConta,
      @Valid Long idEmpresa,
      @Valid Long idPessoa,
      @Valid Long idSetor,
      @Valid Long idSubgrupo,
      @Valid String matricula,
      @Valid String nome,
      @Valid String numeroIdentidade,
      @Valid String sexo,
      @Valid List<Long> status) {
    var countQuery =
        getCountQuery(
            cpf,
            dataNascimento,
            idConta,
            idEmpresa,
            idPessoa,
            idSetor,
            idSubgrupo,
            matricula,
            nome,
            numeroIdentidade,
            sexo,
            status);
    Assert.notNull(countQuery, "TypedQuery must not be null!");
    return (Integer) countQuery.getSingleResult();
  }
}

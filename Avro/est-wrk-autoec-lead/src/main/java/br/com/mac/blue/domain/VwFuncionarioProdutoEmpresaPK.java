package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class VwFuncionarioProdutoEmpresaPK implements Serializable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "EMPRESACARGA_ID", referencedColumnName = "ID_EMPRESACARGA")
  private EmpresaCarga empresaCarga;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUTO_ID", referencedColumnName = "ID_PRODUTO")
  private Produto produto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID_EMPRESA")
  private Empresa empresa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FUNCIONARIO_ID", referencedColumnName = "ID_FUNCIONARIO")
  private Funcionario funcionario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FUNCIONARIOPRODUTO_ID", referencedColumnName = "ID_FUNCIONARIOSPRODUTOS")
  private FuncionarioProduto funcionarioProduto;
}

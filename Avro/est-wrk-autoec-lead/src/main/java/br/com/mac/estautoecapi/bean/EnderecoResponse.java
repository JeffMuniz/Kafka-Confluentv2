package br.com.mac.estautoecapi.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EnderecoResponse implements Serializable {

    private static final long serialVersionUID = 4421061332940919690L;

    private List<Endereco> enderecos;

}

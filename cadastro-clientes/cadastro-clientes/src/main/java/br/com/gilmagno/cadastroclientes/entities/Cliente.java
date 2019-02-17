package br.com.gilmagno.cadastroclientes.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de entidade de representação da tabela de Clientes
 * @author Gilmagno
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "rendimento_mensal", nullable = false)
	private BigDecimal rendimentoMensal;
	
	@Column(name = "risco", nullable = false)
	private String risco;
	
	@Column(name = "endereco", nullable = false)
	private String endereco;
	
	@Column(name = "valor_patrimonio", nullable = false)
	private BigDecimal valorTotalPatrimonio;
	
	@Column(name = "valor_dividas", nullable = false)
	private BigDecimal valorTotalDividas;
	
	@Column(name = "atualmente_empregado", nullable = false)
	private Boolean atualmenteEmpregado;
	
	@Column(name = "tipo", nullable = false)
	private Integer tipoCliente;
}

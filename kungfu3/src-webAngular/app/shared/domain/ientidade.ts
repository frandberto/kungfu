/**
 * Representa uma entidade genérica
 * As propriedades padrões são id e descricao
 * extraPropriedade pode ser qualquer propriedade adicional necessária
 * para a seleção.
 */
export interface IEntidade {
  // Propriedade adicional
  extraPropriedade: string;
  id: string;
  descricao: string;  
}
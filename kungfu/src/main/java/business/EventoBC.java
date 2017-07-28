package business;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import entidade.Evento;
import persistence.EventoDAO;

@Controller
public class EventoBC
{
  @Inject
  private EventoDAO eventoDAO;
  
  public List<Evento> listarEventos()
  {
    return this.eventoDAO.listarTodosNaoExcluidos();
  }

  public Evento buscarPorID(Long idEvento) {
	return eventoDAO.buscarPorID(idEvento);
  }
}
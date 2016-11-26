package business;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import entidade.Evento;
import java.util.List;
import javax.inject.Inject;
import persistence.EventoDAO;

@Controller
public class EventoBC
{
  @Inject
  private EventoDAO eventoDAO;
  
  public List<Evento> listarEventos()
  {
    return this.eventoDAO.listarTodos();
  }
}
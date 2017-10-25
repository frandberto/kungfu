// Declaração da  entidade ErrorMessage
import { TypeMessage } from '../components/messageBar/typeMessage'
export class Message {

  public textMessage: string = "";
  public typeMessage: TypeMessage;
  public showMessage: boolean = false;

  constructor() {
    this.start();
  }

  public start() {
    this.textMessage = "";
    this.typeMessage = "";
    this.showMessage = false;
  }

  public setErrorMessage(msg: string) {
    this.setMessage(msg, TypeMessage.ERROR);
  }

  public setWarningMessage(msg: string) {
    this.setMessage(msg, TypeMessage.WARN);
  }

  public setInfoMessage(msg: string) {
    this.setMessage(msg, TypeMessage.INFO);
  }

  public setMessage(msg: string, typeMessage: string) {
    this.typeMessage = typeMessage;
    this.showMessage = true;
    this.textMessage = msg;
  }

}
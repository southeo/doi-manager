package eu.dissco.core.handlemanager.domain.requests.datacite;

enum Event {
  PUBLISH ("publish"),
  REGISTER("register"),
  HIDE("hide");

  final String action;

  private Event(String action){
    this.action = action;
  }

  @Override
  public String toString() {
    return action;
  }

}

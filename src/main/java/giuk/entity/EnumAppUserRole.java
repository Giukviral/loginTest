package giuk.entity;

public enum EnumAppUserRole {
  ROLE_CLIENT, ROLE_ADMIN, ROLE_MANAGER;

  public String getAuthority(){return name();}
}
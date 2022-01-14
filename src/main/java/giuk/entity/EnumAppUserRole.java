package giuk.entity;

public enum EnumAppUserRole {
  CLIENT, ADMIN, MANAGER;

  public int getNumber(EnumAppUserRole in) {
    if (in == EnumAppUserRole.CLIENT) {
      return 0;
    }
    if (in == EnumAppUserRole.ADMIN) {
      return 1;
    }
    if (in == EnumAppUserRole.MANAGER) {
      return 2;
    }
    return 0;
  }
}
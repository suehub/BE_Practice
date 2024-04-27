package community.server.domain;

public enum TxStatus {
  START{
    public TxStatus next() {
      return EXECUTED;
    }
  },
  EXECUTED{
    public TxStatus next() {
      return CHECKED;
    }
  },
  CHECKED{
    public TxStatus next() {
      return COMMIT;
    }
  },
  COMMIT{
    public TxStatus next() {
      return ROLLBACK;
    }
  },
  ROLLBACK
}

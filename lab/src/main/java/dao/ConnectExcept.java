package dao;

public class ConnectExcept extends Exception{
    public ConnectExcept(Exception e) {
        super(e);
    }
}
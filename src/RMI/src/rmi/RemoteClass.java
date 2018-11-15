/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Thanh Doan
 */
public class RemoteClass extends UnicastRemoteObject implements RemoteInterface{
    public RemoteClass() throws RemoteException{
        super();
    }

    @Override
    public double Tong(double a, double b) throws RemoteException {
       return a+b;
    }

    @Override
    public double Hieu(double a, double b) throws RemoteException {
        return a-b;
        
    }

    @Override
    public double Nhan(double a, double b) throws RemoteException {
      return a*b;
    }

    @Override
    public double Chia(double a, double b) throws RemoteException {
       return a/b;
    }
    
}

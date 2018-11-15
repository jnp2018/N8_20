/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Thanh Doan
 */
public interface RemoteInterface extends Remote{
public double Tong(double a, double b) throws RemoteException;
public double Hieu(double a, double b) throws RemoteException;
public double Nhan(double a, double b) throws RemoteException;
public double Chia(double a, double b) throws RemoteException;

    
    
}

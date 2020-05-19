/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.vogella.itext.write;

/**
 *
 * @author isaacreyes
 */
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
public class CurrentDateTimeExample1 {  
  public static void main(String[] args) {  
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   LocalDateTime now = LocalDateTime.now();
   System.out.println(dtf.format(now));
  }  
}  

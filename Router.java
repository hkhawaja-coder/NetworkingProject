/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingproject;

import java.util.*;

/**
 *
 * @author alexr
 */
public class Router {
    
    private Map<String, String> table;
    
    public Router() {
        
    }
    
    public Router(String[] table) {
        setTable(table);
    }
    
    public int route(String ip) {
        int i = 0;
        for (String key : table.keySet()) {
            if (key.equals(and(ip, table.get(key)))) {
                return i;
            }
            i++;
        }
        return i;
    }
    
    public String and(String ip, String mask) {
        ip = getBinary(ip);
        
        StringBuilder newIP = new StringBuilder();
        
        for (int i = 0; i < 32; i++) {
            newIP.append(Integer.parseInt(ip.charAt(i) + "") & Integer.parseInt(mask.charAt(i) + ""));
        }
        
        return formatIP(newIP.toString());
        
    }
    
    public String getMask(int mask) {
        StringBuilder newMask = new StringBuilder("00000000000000000000000000000000");
        
        for (int i = 0; i < mask; i++) {
            newMask.replace(i, i+1, "1");
        }
        
        return newMask.toString();
    }
    
    public String formatIP(String binary) {
        StringBuilder newBinary = new StringBuilder();
        StringBuilder finalBinary = new StringBuilder();
        
        for (int i = 0; i < 32; i++) {
            newBinary.append(binary.charAt(i));
            
            if((i+1) % 8 == 0) {
                finalBinary.append(Integer.parseInt(newBinary.toString(), 2));
                finalBinary.append(".");
                newBinary = new StringBuilder();
            }
        }
        
        return finalBinary.substring(0, finalBinary.length() - 1);
    }
    
    public String getBinary(String ip) {
        String[] section = ip.split("\\.");
        StringBuilder newIP = new StringBuilder();
        
        newIP.append(getBinary(section[0]));
        newIP.append(getBinary(section[1]));
        newIP.append(getBinary(section[2]));
        newIP.append(getBinary(section[3]));
        
        return newIP.toString();
    }
    
    public String BinaryString(String section) {
        return String.format("%8s", Integer.toBinaryString(Integer.parseInt(section))).replace(' ', '0');
    }
    
    public void setTable(String[] table) {
        parseTable(table);
    }
    
    public void parseTable(String[] t) {
        table = new LinkedHashMap<>();
        
        for (String row: t) {
            String[] column = row.split("/");
            table.put(column[0], getMask(Integer.parseInt(column[1])));
        }
        
    }
}

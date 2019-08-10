/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core.workqueue;

/**
 *
 * @author Carlos
 */
public class JobParam {
    private String name;
    private String value;

    public JobParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public JobParam() {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    
    
}

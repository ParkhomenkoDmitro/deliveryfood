package com.parkhomenko.common.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by dmytro on 22.07.16.
 */
public class Client extends User implements Serializable {
    //TODO: ManyToMany will be here
    private Set<ClientAddress> addresses;

    public Client() {
    }

    public Set<ClientAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<ClientAddress> addresses) {
        this.addresses = addresses;
    }
}

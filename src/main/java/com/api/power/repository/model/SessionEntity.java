package com.api.power.repository.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SessionEntity {

    private Data data;

    @Getter @Setter
    public static class Data {

        private Customer[] customer;

        @Getter @Setter
        public static class Customer {

            private Connection connection;

            @Getter @Setter
            public static class Connection {
                private String connection_id;
            }

        }

    }
    
}

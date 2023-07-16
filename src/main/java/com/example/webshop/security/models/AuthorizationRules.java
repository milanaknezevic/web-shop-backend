package com.example.webshop.security.models;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizationRules {
    List<Rule> rules;
}
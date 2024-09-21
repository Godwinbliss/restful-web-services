package com.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
    // URI versioning - Twitter Style
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    //Request Parameter versioning - Amazon Style
    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParam(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParam(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // (Custom) headers versioning - Microsoft Style
    @GetMapping(path = "/person/headers", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonHeaders(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person/headers", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonHeaders(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Media type versioning (a.k.a "content negotiation"
    // or "accept header") - Github Style

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeaders(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeaders(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

}

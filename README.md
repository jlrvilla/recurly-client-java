# Recurly

This library is the Java client to the v3, aka API next, aka PAPI, version of Recurly's API. Parts of this gem are generated
by the `recurly-client-gen` project.

## Getting Started

Read this before starting to get an overview of how to use this library.

### Creating a client

Client instances are now explicitly created and managed as opposed to the previous approach of opaque, statically
initialized clients. This makes multithreaded environments a lot easier and also provides one place where every
operation on recurly can be found (rather than having them spread out amongst classes).

`ClientBuilder.build` initializes a new client. It requires an API key:

```java
final Client client = ClientBuilder.build("83749879bbde395b5fe0cc1a5abf8e5");
```

### Examples (more docs to come)


```java
final Client client = ClientBuilder.build("83749879bbde395b5fe0cc1a5abf8e5");

// Here is an example of async call
service.getSite("subdomain-ben").enqueue(new Callback<Site>() {
    public void onResponse(Call<Site> call, Response<Site> response) {
        System.out.println("Response Code: " + response.code());
        System.out.println("Body: "+ response.raw());
        Site site = response.body();
        System.out.println("Site id: " + site.getId());
        System.out.println("Site subdomain: " + site.getSubdomain());
        System.out.println("Site createdAt: " + site.getCreatedAt());
        System.out.println("Site updatedAt: " + site.getCreatedAt());
        System.out.println("Site address.country: " + site.getAddress().getCountry());
    }
    public void onFailure(Call<Site> call, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
});

try {
    // Here is an example of creating something with a request class
    final CreateAccount accountRequest = new CreateAccount();
    accountRequest.setCode("benjamin.dumonde1234567@example.com");
    accountRequest.setFirstName("benjamin");
    accountRequest.setLastName("derp");

    final Address address = new Address();
    address.setCity("New Orleans");
    address.setStreet1("123 Main St");
    address.setCountry("US");
    address.setRegion("LA");

    accountRequest.setAddress(address);

    // here is an example of a synchronous call
    Response<Account> response = client.createAccount("subdomain-ben", accountRequest).execute();
    final Account newAccount = response.body();
    System.out.println("Account id: "+newAccount.getId());
    System.out.println("Account created: "+newAccount.getCreatedAt());
    System.out.println("Account updated: "+newAccount.getUpdatedAt());
    System.out.println("Account address street: "+newAccount.getAddress().getStreet1());
    System.out.println("Account address city: "+newAccount.getAddress().getCity());
    System.out.println("Account address region: "+newAccount.getAddress().getRegion());
    System.out.println("Account address country: "+newAccount.getAddress().getCountry());
} catch (IOException e) {
    e.printStackTrace();
}

// Here is an example of pagination
service.getSites().enqueue(new Callback<Pager<Site>>() {
    public void onResponse(Call<Pager<Site>> call, Response<Pager<Site>> response) {
        Pager<Site> sites = response.body();
        // You can use normal Java Iterable form
        for (Site site : sites) {
            System.out.println("Site: " + site.getId());
        }
    }
    public void onFailure(Call<Pager<Site>> call, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
});
```

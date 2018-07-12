package com.recurly.v3.resources;

import com.google.gson.annotations.SerializedName;
import com.recurly.v3.Resource;
import org.joda.time.DateTime;
import java.util.List;
import java.util.Map;

public class ShippingAddress extends Resource {

  @SerializedName("account_id")
  private String accountId;

  @SerializedName("city")
  private String city;

  @SerializedName("company")
  private String company;

  @SerializedName("country")
  private String country;

  @SerializedName("created_at")
  private DateTime createdAt;

  @SerializedName("email")
  private String email;

  @SerializedName("first_name")
  private String firstName;

  @SerializedName("id")
  private String id;

  @SerializedName("last_name")
  private String lastName;

  @SerializedName("nickname")
  private String nickname;

  @SerializedName("phone")
  private String phone;

  @SerializedName("postal_code")
  private String postalCode;

  @SerializedName("region")
  private String region;

  @SerializedName("street1")
  private String street1;

  @SerializedName("street2")
  private String street2;

  @SerializedName("updated_at")
  private DateTime updatedAt;

  @SerializedName("vat_number")
  private String vatNumber;


  public String getAccountId() { return this.accountId; }
  public void setAccountId(final String accountId) { this.accountId = accountId; }

  public String getCity() { return this.city; }
  public void setCity(final String city) { this.city = city; }

  public String getCompany() { return this.company; }
  public void setCompany(final String company) { this.company = company; }

  public String getCountry() { return this.country; }
  public void setCountry(final String country) { this.country = country; }

  public DateTime getCreatedAt() { return this.createdAt; }
  public void setCreatedAt(final DateTime createdAt) { this.createdAt = createdAt; }

  public String getEmail() { return this.email; }
  public void setEmail(final String email) { this.email = email; }

  public String getFirstName() { return this.firstName; }
  public void setFirstName(final String firstName) { this.firstName = firstName; }

  public String getId() { return this.id; }
  public void setId(final String id) { this.id = id; }

  public String getLastName() { return this.lastName; }
  public void setLastName(final String lastName) { this.lastName = lastName; }

  public String getNickname() { return this.nickname; }
  public void setNickname(final String nickname) { this.nickname = nickname; }

  public String getPhone() { return this.phone; }
  public void setPhone(final String phone) { this.phone = phone; }

  public String getPostalCode() { return this.postalCode; }
  public void setPostalCode(final String postalCode) { this.postalCode = postalCode; }

  public String getRegion() { return this.region; }
  public void setRegion(final String region) { this.region = region; }

  public String getStreet1() { return this.street1; }
  public void setStreet1(final String street1) { this.street1 = street1; }

  public String getStreet2() { return this.street2; }
  public void setStreet2(final String street2) { this.street2 = street2; }

  public DateTime getUpdatedAt() { return this.updatedAt; }
  public void setUpdatedAt(final DateTime updatedAt) { this.updatedAt = updatedAt; }

  public String getVatNumber() { return this.vatNumber; }
  public void setVatNumber(final String vatNumber) { this.vatNumber = vatNumber; }

}

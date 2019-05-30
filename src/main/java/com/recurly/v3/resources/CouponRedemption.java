package com.recurly.v3.resources;

import com.google.gson.annotations.SerializedName;
import com.recurly.v3.Resource;
import org.joda.time.DateTime;
import java.util.List;
import java.util.Map;

public class CouponRedemption extends Resource {

  @SerializedName("account")
  private AccountMini account;

  @SerializedName("coupon")
  private Coupon coupon;

  @SerializedName("created_at")
  private DateTime createdAt;

  @SerializedName("currency")
  private String currency;

  @SerializedName("discounted")
  private String discounted;

  @SerializedName("id")
  private String id;

  @SerializedName("object")
  private String object;

  @SerializedName("removed_at")
  private DateTime removedAt;

  @SerializedName("state")
  private String state;

  @SerializedName("updated_at")
  private DateTime updatedAt;


  public AccountMini getAccount() { return this.account; }
  public void setAccount(final AccountMini account) { this.account = account; }

  public Coupon getCoupon() { return this.coupon; }
  public void setCoupon(final Coupon coupon) { this.coupon = coupon; }

  public DateTime getCreatedAt() { return this.createdAt; }
  public void setCreatedAt(final DateTime createdAt) { this.createdAt = createdAt; }

  public String getCurrency() { return this.currency; }
  public void setCurrency(final String currency) { this.currency = currency; }

  public String getDiscounted() { return this.discounted; }
  public void setDiscounted(final String discounted) { this.discounted = discounted; }

  public String getId() { return this.id; }
  public void setId(final String id) { this.id = id; }

  public String getObject() { return this.object; }
  public void setObject(final String object) { this.object = object; }

  public DateTime getRemovedAt() { return this.removedAt; }
  public void setRemovedAt(final DateTime removedAt) { this.removedAt = removedAt; }

  public String getState() { return this.state; }
  public void setState(final String state) { this.state = state; }

  public DateTime getUpdatedAt() { return this.updatedAt; }
  public void setUpdatedAt(final DateTime updatedAt) { this.updatedAt = updatedAt; }

}

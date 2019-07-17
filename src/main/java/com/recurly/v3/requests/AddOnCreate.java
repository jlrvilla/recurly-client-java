/**
 * This file is automatically created by Recurly's OpenAPI generation process
 * and thus any edits you make by hand will be lost. If you wish to make a
 * change to this file, please create a Github issue explaining the changes you
 * need and we will usher them to the appropriate places.
 */
package com.recurly.v3.requests;

import com.recurly.v3.resources.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.recurly.v3.Request;
import org.joda.time.DateTime;
import java.util.List;
import java.util.Map;

public class AddOnCreate extends Request {

  @SerializedName("accounting_code")
  @Expose
  private String accountingCode;

  @SerializedName("code")
  @Expose
  private String code;

  @SerializedName("currencies")
  @Expose
  private List<AddOnPricing> currencies;

  @SerializedName("default_quantity")
  @Expose
  private Integer defaultQuantity;

  @SerializedName("display_quantity")
  @Expose
  private Boolean displayQuantity;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("plan_id")
  @Expose
  private String planId;

  @SerializedName("tax_code")
  @Expose
  private String taxCode;


  public String getAccountingCode() { return this.accountingCode; }
  public void setAccountingCode(final String accountingCode) { this.accountingCode = accountingCode; }

  public String getCode() { return this.code; }
  public void setCode(final String code) { this.code = code; }

  public List<AddOnPricing> getCurrencies() { return this.currencies; }
  public void setCurrencies(final List<AddOnPricing> currencies) { this.currencies = currencies; }

  public Integer getDefaultQuantity() { return this.defaultQuantity; }
  public void setDefaultQuantity(final Integer defaultQuantity) { this.defaultQuantity = defaultQuantity; }

  public Boolean getDisplayQuantity() { return this.displayQuantity; }
  public void setDisplayQuantity(final Boolean displayQuantity) { this.displayQuantity = displayQuantity; }

  public String getName() { return this.name; }
  public void setName(final String name) { this.name = name; }

  public String getPlanId() { return this.planId; }
  public void setPlanId(final String planId) { this.planId = planId; }

  public String getTaxCode() { return this.taxCode; }
  public void setTaxCode(final String taxCode) { this.taxCode = taxCode; }

}

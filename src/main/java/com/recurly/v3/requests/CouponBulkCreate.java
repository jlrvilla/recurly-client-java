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

public class CouponBulkCreate extends Request {

  @SerializedName("number_of_unique_codes")
  @Expose
  private Integer numberOfUniqueCodes;


  public Integer getNumberOfUniqueCodes() { return this.numberOfUniqueCodes; }
  public void setNumberOfUniqueCodes(final Integer numberOfUniqueCodes) { this.numberOfUniqueCodes = numberOfUniqueCodes; }

}

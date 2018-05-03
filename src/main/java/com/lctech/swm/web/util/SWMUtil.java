package com.lctech.swm.web.util;

import com.lctech.swm.config.Constants;
import com.lctech.swm.controller.UsersController;
import com.lctech.swm.domain.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class SWMUtil {

	public static Date formatDate(String date) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		return fm.parse(date);
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		// String
		if (obj instanceof String && ((String) obj).length() <= 0) {
			return true;
		}

		return false;
	}

	public static String getDisplayPeriodDate(Integer month, Integer year){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String monthString = month < 10 ? "0" + month.toString() : month.toString();
        String startDate = "01/" + monthString + "/" + year.toString();
        LocalDate localDate = LocalDate.parse(startDate,DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());

        return startDate + " - " + lastDayOfMonth.format(dateFormatter);
    }

    /**
     * Append all field address.
     *
     * @param user obj.
     * @return full address.
     */
    public static String getDisplayAddress(Users user){

        String spiltChar = " - ";
        StringBuilder address = new StringBuilder();
        if (!StringUtils.isEmpty(user.getAddressNo())){
            address.append("Số nhà: ").append(user.getAddressNo()).append(spiltChar);
        }
        if (!StringUtils.isEmpty(user.getAddressLand())){
            address.append("Ngõ: ").append(user.getAddressLand()).append(spiltChar);
        }
        if (!StringUtils.isEmpty(user.getAddressAlley())){
            address.append("Ngách: ").append(user.getAddressAlley()).append(spiltChar);
        }
        if (!StringUtils.isEmpty(user.getAddressAlley2())){
            address.append("Hẻm: ").append(user.getAddressAlley2()).append(spiltChar);
        }
        if (!StringUtils.isEmpty(user.getAddressStreet())){
            address.append("Đường phố, Khu ĐT(xóm,thôn,tổ): ").append(user.getAddressStreet());
        }

        return address.toString();
    }

}

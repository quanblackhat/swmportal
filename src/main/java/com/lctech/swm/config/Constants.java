package com.lctech.swm.config;

/**
 * Application constants.
 */

public class Constants {
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String ACTION_VIEW_ADD = "viewAdd";
	public static final String ACTION_VIEW_EDIT = "viewEdit";
	public static final String ACTION_VIEW = "view";
	public static final String ACTION_SEARCH = "search";
	public static final String ACTION_VIEW_CREATE_FORM = "viewCreateForm";
	public static final String ACTION_VIEW_ASSIGN = "viewAssign";
	
	public static final int LEVEL_TYPE_ADMIN = 1;
	public static final int LEVEL_TYPE_COMPANY = 2;
	public static final int LEVEL_TYPE_AGENT = 3;
	public static final int LEVEL_TYPE_USER = 4;

	public static final String USES_CODE_SH = "SH";
	public static final String USES_CODE_CQHC = "CQHC";
	public static final String USES_CODE_SNCC = "SXCC";
	public static final String USES_CODE_SX = "SX";
	public static final String USES_CODE_KDDV = "KDDV";

	//On 00:01:00 of first day of month
    public static final String BACKUP_DATA_SCHEDULER = "0 0 2 1 * *";

    public static final String DATE_FORMAT = "dd/MM/yyyy";

}

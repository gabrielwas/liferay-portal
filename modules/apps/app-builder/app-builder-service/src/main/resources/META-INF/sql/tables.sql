create table AppBuilderApp (
	uuid_ VARCHAR(75) null,
	appBuilderAppId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	deDataLayoutId LONG,
	deDataListViewId LONG,
	settings_ VARCHAR(75) null
);
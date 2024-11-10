create table Flight (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	flightId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	flightNumber VARCHAR(75) null,
	active_ BOOLEAN,
	capacity INTEGER,
	flightDate DATE null
);
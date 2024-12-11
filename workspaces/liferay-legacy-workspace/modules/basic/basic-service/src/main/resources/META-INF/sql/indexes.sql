create index IX_2930DF73 on Flight (active_);
create unique index IX_6C5559A1 on Flight (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create unique index IX_869019CC on Flight (uuid_[$COLUMN_LENGTH:75$], groupId);
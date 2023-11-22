package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.query.IndexHintStep;
import com.liferay.petra.sql.dsl.query.JoinStep;

public interface DefaultIndexHintStep extends DefaultWhereStep, IndexHintStep {

	@Override
	public default JoinStep withIndexHint(String indexHint){
		return new IndexHint(this, indexHint);
	}

}

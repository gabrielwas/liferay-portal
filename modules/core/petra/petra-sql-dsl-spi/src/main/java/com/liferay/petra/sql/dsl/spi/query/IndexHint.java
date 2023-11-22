package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.IndexHintStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

public class IndexHint extends BaseASTNode implements DefaultJoinStep {

	public IndexHint(IndexHintStep indexHintStep, String string) {

		super(indexHintStep);

		_indexHint = Objects.requireNonNull(string);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_indexHint);

	}

	private final String _indexHint;
}

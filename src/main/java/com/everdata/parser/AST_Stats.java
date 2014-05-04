/* Generated By:JJTree: Do not edit this line. AST_Stats.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.everdata.parser;

import java.util.ArrayList;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;

import com.everdata.command.CommandException;
import com.everdata.command.Field;
import com.everdata.command.Function;

public class AST_Stats extends SimpleNode {

	AbstractAggregationBuilder[] internalStats = null;
	String[] bucketFields = new String[0];
	ArrayList<Function> funcs = new ArrayList<Function>();
	int mincount = 1;
	ArrayList<String> statsFields = new ArrayList<String>();
	
	public Function count = null;
	public String[] bucketFields(){
		return bucketFields; 
	}
	
	public String[] statsFields(){
		return statsFields.toArray(new String[statsFields.size()]);
	}

	public AST_Stats(int id) {
		super(id);
	}

	public AST_Stats(CommandParser p, int id) {
		super(p, id);
	}
	
	

	public static AbstractAggregationBuilder newSum(Function func) {
		SumBuilder sum;
		if (func.fieldtype == Field.SCRIPT)
			sum = AggregationBuilders.sum(Function.genStatField(func))
					.script(func.field);
		else
			sum = AggregationBuilders.sum(Function.genStatField(func))
					.field(func.field);

		return sum;
	}

	public static AbstractAggregationBuilder newAvg(Function func) {
		AvgBuilder avg;
		if (func.fieldtype == Field.SCRIPT)
			avg = AggregationBuilders.avg(Function.genStatField(func))
					.script(func.field);
		else
			avg = AggregationBuilders.avg(Function.genStatField(func))
					.field(func.field);

		return avg;
	}

	public static AbstractAggregationBuilder newMin(Function func) {
		MinBuilder min;
		if (func.fieldtype == Field.SCRIPT)
			min = AggregationBuilders.min(Function.genStatField(func))
					.script(func.field);
		else
			min = AggregationBuilders.min(Function.genStatField(func))
					.field(func.field);		
		return min;
	}

	public static AbstractAggregationBuilder newMax(Function func) {
		MaxBuilder max;
		if (func.fieldtype == Field.SCRIPT)
			max = AggregationBuilders.max(Function.genStatField(func))
					.script(func.field);
		else
			max = AggregationBuilders.max(Function.genStatField(func))
					.field(func.field);
		return max;
	}

	private void traverseAST() {

		for (Node n : children) {
			if (n instanceof AST_ByIdentList) {

				bucketFields = ((AST_ByIdentList) n).getNames();

			} else if (n instanceof AST_StatsFunc) {
				funcs.add(((AST_StatsFunc) n).func);

			}
		}
	}

	private AbstractAggregationBuilder[] genAggregation() throws CommandException {

		traverseAST();

		TermsBuilder buckets = null;
		ArrayList<AbstractAggregationBuilder> stats = new ArrayList<AbstractAggregationBuilder>();

		AbstractAggregationBuilder function = null;

		if (bucketFields.length > 0) {
			buckets = AST_Top.newTerms("statsWithBy", -1, bucketFields);
			buckets.minDocCount(mincount);			
		}

		for (Function func : funcs) {
			
			switch (func.type) {
			case Function.COUNT:
				count = func;
				continue;
			case Function.SUM:
				function = newSum(func);
				break;
			case Function.AVG:
				function = newAvg(func);
				break;
			case Function.MIN:
				function = newMin(func);
				break;
			case Function.MAX:
				function = newMax(func);
				break;

			case Function.DC:
				throw new CommandException("不支持 DC 函数");
			}
			statsFields.add( Function.genStatField(func) );

			if (buckets != null)
				buckets.subAggregation(function);
			else{				
				stats.add(function);
			}
		}
		
		if(buckets == null){			
			return stats.toArray(new AbstractAggregationBuilder[stats.size()]);
		}else{
			AbstractAggregationBuilder[] bucketArray = new AbstractAggregationBuilder[1];
			bucketArray[0] = buckets;
			return bucketArray;
		}


	}

	public AbstractAggregationBuilder[] getStats() throws CommandException {

		if (internalStats == null)
			internalStats = genAggregation();

		return internalStats;
	}

}
/*
 * JavaCC - OriginalChecksum=663713222972f6d3f2e7821e2216d7f2 (do not edit this
 * line)
 */

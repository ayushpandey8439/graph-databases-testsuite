t = System.nanoTime();	
count = g.E.count().next();	
exec_time = System.nanoTime() - t;
result_row = [ DATABASE, DATASET, QUERY,"0", ITERATION, "0", String.valueOf(exec_time), String.valueOf(count)];
println result_row.join(',');

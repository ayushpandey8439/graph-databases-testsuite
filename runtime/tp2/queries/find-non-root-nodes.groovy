t = System.nanoTime();
c = g.V.out.dedup().count();
exec_time = System.nanoTime() - t;
result_row = [ DATABASE, DATASET, QUERY,"0", ITERATION, "0", String.valueOf(exec_time), String.valueOf(c)];
println result_row.join(',');

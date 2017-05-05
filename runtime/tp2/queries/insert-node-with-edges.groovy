SID = System.env.get("SID").toInteger();
def execute_query(g,id,i,ORDER_j,DATABASE,DATASET,QUERY,ITERATION,OBJECT_ARRAY,SID,UID_NAME, UID_VALUE){
	v = g.v(id);
	property_map = v.map();
	property_map[UID_NAME] = UID_VALUE;
	inEdges = []
	index = 0;
	outEdges = [];
	outdex = 0;
	v.outE.fill(outEdges)
	v.inE.fill(inEdges)
	t = System.nanoTime();
	new_v = g.addVertex(null,property_map);
	if(!SKIP_COMMIT){
		try {
			g.commit();
		} catch (MissingMethodException e) {
			System.err.println("Does not support g.commit(). Ignoring.");
		}
	}
	exec_time = System.nanoTime() - t;
	result_row = [ DATABASE, DATASET, QUERY, String.valueOf(SID), ITERATION, String.valueOf(ORDER_j), String.valueOf(exec_time)," ", String.valueOf(OBJECT_ARRAY[i])];
	println result_row.join(',');
	t = System.nanoTime();
	outEdges.each{
        edge ->
            g.addEdge(null, new_v, edge.outV.next(), edge.label, edge.map())
	}
	if(!SKIP_COMMIT){
		try {
			g.commit();
		} catch (MissingMethodException e) {
			System.err.println("Does not support g.commit(). Ignoring.");
		}
	}
	exec_time = System.nanoTime() - t;
	result_row = [ DATABASE, DATASET, QUERY, String.valueOf(SID), ITERATION, String.valueOf(ORDER_j), String.valueOf(exec_time)," ", String.valueOf(OBJECT_ARRAY[i]),String.valueOf(outEdges.size()), "in"];
	println result_row.join(',');
	t = System.nanoTime();
        inEdges.each{
          edge -> g.addEdge(null, edge.inV.next(), new_v, edge.label, edge.map());
       }
	if(!SKIP_COMMIT){
		try {
			g.commit();
		} catch (MissingMethodException e) {
			System.err.println("Does not support g.commit(). Ignoring.");
		}
	}
	exec_time = System.nanoTime() - t;
	result_row = [ DATABASE, DATASET, QUERY, String.valueOf(SID), ITERATION, String.valueOf(ORDER_j), String.valueOf(exec_time)," ", String.valueOf(OBJECT_ARRAY[i]),String.valueOf(inEdges.size()), "out"];
	println result_row.join(',');
}
if (SID == NODE_LID_ARRAY.size()) {
	order_j = 1;
	for (i in RAND_ARRAY) {
         execute_query(g,NODE_LID_ARRAY[i],i,order_j,DATABASE,DATASET,QUERY,ITERATION,NODE_ARRAY,SID,uid_field,MAX_UID);
         order_j++;
         MAX_UID = MAX_UID + 1;
	}
} else {
    execute_query(g,NODE_LID_ARRAY[SID],SID,0,DATABASE,DATASET,QUERY,ITERATION,NODE_ARRAY,SID,uid_field,MAX_UID);
}

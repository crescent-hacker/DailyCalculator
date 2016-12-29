#Optimization solution:

##1. When reading file (pseudo code):
sh'''
	function readFile(String path):
		int bufferSize = 1024*1024*100; //100MB
		FileChannel fileChannel = new RandomAccessFile(new File(path), "r").getChannel();
    	ByteBuffer readBuffer = ByteBuffer.allocate(bufferSize);
    	readLinesOfFile(readBuffer,fileChannel,bufferSize);
'''
##2. Define readLine function for reading line in a buffer (pseudo code):
sh'''

	public class StatModel{
		......
    	private Map<Long,PeriodData> periodDataMap;
    	public StatModel(){
    		periodDataMap = new ConcurrentHashMap<>();
    	}
    	......
	}
'''

sh'''
	function readLinesOfFile(readBuffer,fileChannel,bufferSize,statModel):
		int lineBreak = 10; //the ascii of line break
		List<String> lines = new ArrayList<String>(); // store lines in a buffer
		byte[] concatLine; // temporarily store a concat line
		byte[] incompleteLine = new byte[0]; // temporarily store the rest part of a buffer which is not a complete line with \n

		//start reading lines from buffers
		while(fileChannel.read(readBuffer)!=-1):
			int readSize = readBuffer.position();
			byte[] readContent = readBuffer.array();

			boolean isBreakExist = false; //check if there is line break during reading lines in one buffer
			int startPos = 0; //start position index for the next line reading loop

			//start reading lines in a buffer
			for(int i=0;i<readSize;i++):
				//detect \n
				if(readContent[i] == lineBreak):
					isBreakExist = true;
					int incompleteLineSize = incompleteLine.length;
					//the current line size; the line could be the last \n to current \n, or the last buffer break to current \n 
					int lineSize = i - startPos;
					//concat the incomplete line and current line (if no incomplete line, that's fine)
					concatLine = new byte[incompleteLineSize+lineSize];
					//put incomplete line as the first part of concatLine
					System.arraycopy(incompleteLine,0,concatLine,0,incompleteLineSize);
					//put current line into concatline as second part
					System.arraycopy(readContent,startPos,concatLine,incompleteLineSize,lineSize);
					//put concat line into list
					String lineStr = new String(concatLine);
					lines.add(lineStr);
					//update start position with offset 1 to pass \n
					startPos = i + 1;

				if(isBreakExist):
					//handle situation that the end of the buffer is not \n
					incompleteLine = new byte[readContent.length - startPos]; //start pos here is the position pass the last \n in buffer
					System.arraycopy(readContent,startPos,incompleteLine,0,incompleteLine.length);
				else:
					//handle situation that only one line without line break in buffer
					byte[] tmpStore = incompleteLine;
					incompleteLine = new byte[readContent.length+tmpStore.length];
					System.arraycopy(tmpStore,0,incompleteLine,0,tmpStore.length);
					System.arraycopy(readContent,0,incompleteLine,tmpStore.length,readContent.length);

			//handle situation that there is no \n in the end line
			if(incompleteLine != null && incompleteLine.length > 0):
                String lineStr = new String(incompleteLine);
				if(!"Maximum result limit reached,".equals(lineStr))
	                dataList.add(line);

			//clear buffer
			readBuffer.clear();

			//launch multi-thread analyzer to analyse; block the reading thread until all sub-thread done
			launchAnalysis(lines,statModel);
'''


##3. multi-thread analysis (pseudo code):

sh'''
	function launchAnalysis(lines,statModel):
		final int THREAD_LIMIT = 4;
		CountDownLatch singleTaskCount = new CountDownLatch(1);
		CountDownLatch allTaskCount = new CountDownLatch(THREAD_LIMIT);
		//create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_LIMIT);
        int share = lines.size()/THREAD_LIMIT; // the lines for each thread
        for(int i=0;i<THREAD_LIMIT;i++):
        	int startIdx = i * share;
        	int endIdx;
        	if(i == THREAD_LIMIT-1)
        		endIdx = lines.size();
        	else{
        		endIdx = startIdx + share;
        	}
        	//cut lines list into pieces for each thread to process
        	List<String> subLines = lines.subList(startIdx,endIdx);
        	DataConvertor convertor = new DataConvertor(currentTaskCount,allTaskCount,subLines,statModel);
        	executor.execute(convertor);
        	//begin countdown
        	singleTaskCount.countDown();
        	try{
        		allTaskCount.await();
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}finally{
        		executor.shutdown();
        	}
'''


sh'''
	public class DataConvertor implements Runnable{
		private List<String> lines;
		private CountDownLatch currentTaskCount;
		private CountDownLatch allTaskCount;
		private StatModel statModel;

		public DataConvertor(currentTaskCount,allTaskCount,lines,statModel){
			this.currentTaskCount = currentTaskCount;
			this.allTaskCount = allTaskCount;
			this.lines = lines;
			this.statModel = statModel
		}

		@Override
		public void run(){
			try{
				currentTaskCount.await();
				//convert record into concurrencyMap
				for(String line:lines):
					Record record = processLineData(line);
			        statModel.addPeriodData(record);

			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				allTaskCount.countDown();
			}
		}

		public Record processLineData(String line):
			convert line into record obj

	}
'''


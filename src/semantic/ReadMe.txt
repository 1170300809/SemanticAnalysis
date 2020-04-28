1，实验的架构
	1.1 实验的算法，语义预测分析流程
		注意：
			1.top是栈顶在列表中的标号，为0，top+1，top+2代表栈顶下第一个，第二个元素，以此类推
			2，弹栈时生成对应的符号于符号表中，以及三地址指令和四元式序列。 
	
		针对每一个Token，进入循环
			按照语义预测分析表将SDT的对应右部加入栈中
			
			
			若栈顶是 非终结符
				按照语义预测分析表找到 栈顶  非终结符 的相应右部
				将 非终结符的值 赋给 其右部中的 需要该属性值的 动作记录
				弹栈，
				将其右部按照从右到左的顺序依次压入栈中
				进入下一循环
			若栈顶是 终结符号
				若 终结符号 中的终结符与Token匹配成功，
				将值赋给 终结符号 的value，
				将该属性的值备份到 top+1 的 动作记录中
				弹栈， 
				处理栈顶的 动作记录（可以直接执行的栈操作），
				弹栈。
				进入下一循环
			若栈顶是 综合属性或继承属性
				将该属性的值备份到 top+1 的 动作记录中
				弹栈
				处理栈顶的 动作记录
				进入下一循环
			
		
	1.2 工程类的分布
		
		
		语法分析栈：
			实现语义分析中的 输出：符号表(SymbolList)
			实现语义分析中的 三地址指令和四元组(TupleOfFour)
				成员：
					String elements[4]	四元组的变量
					
				方法：
					String getElement(int i)	返回elements的第i个元素
					void SetElement(String s1，String s2,String s3,String s4)	通过s1,s2,s3,s4设置elements的值。
					SymbolList()	构造函数，无参数，四元组默认为:(-,-,-,-)
					SymbolList(String s1，String s2,String s3,String s4)	构造函数,通过s1,s2,s3,s4设置elements的值。
					
			实现语义分析栈中的 符号值对(SymbolValue)
				成员：
					StackSymbol symbol;	符号值对的符号变量
					Value value;	符号值对的值变量
					
				方法：
					equals(Object a)	若StackSymbol相同，Value也相同，则StackSymbol相同
					action(SemanticStack s)	执行symbol中表达式的语义动作(symbol.action())
			
			实现语义分析栈 (SemanticStack)
				成员：
					List<符号值对>	符号值对的列表，栈的容器结构
				功能：
					public push()	//将一个SymbolValue对象压入栈中，将栈中原来的所有元素往下移动一格，然后将该对象赋给List第一个元素
					public pop()	//将栈顶(标号为0)的符号值对弹出，并返回该符号值对
					public getFirst()	//返回栈顶的符号值对
					public getSymbol（int i）	//返回栈的第i个元素
			语义分析总过程(SemanticAnalyzer)
				功能：/*未完待续*/
					
					
	
	
2，实验流程
	1）
				
				
				
	2）
		
	3）
		
	4）
		语法分析过程函数编写
	5）
		GUI的整合

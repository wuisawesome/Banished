package com.banished.core;

import java.util.EmptyStackException;
import java.util.Stack;

import com.banished.Banished;
import com.banished.levels.LevelState;
public abstract class State
{
	private static Stack<State> States = new Stack<State>();
	
	public static void EnterState(State state)
	{
		States.push(state);
		if(state instanceof LevelState)
			((LevelState)state).Initialize();
	}
	public static State ExitState()
	{
		try { return States.pop(); }
		catch (EmptyStackException e) { }
		return null;
	}
	public static State GetCurrentState()
	{
		try { return States.peek(); }
		catch (EmptyStackException e) { return null; }
	}
	
	public static boolean UpdateState(double frameTime)
	{
		// returns true if the application should exit.
		if (States.size() == 0)
			return true;
		States.peek().Update(frameTime);
		return false;
	}
	public static void DrawState()
	{
		State state = GetCurrentState();
		if (state == null) return;
		state.Draw();
	}
	
	protected static void ExitApplication()
	{
		Banished.exitApp();
	}
	
	protected static boolean NoStates()
	{
		return States.isEmpty();
	}
	public abstract void Update(double frameTime);
	public abstract void Draw();
}

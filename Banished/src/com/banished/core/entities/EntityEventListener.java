package com.banished.core.entities;

public interface EntityEventListener
{
	void onTouched(Entity sender);
	void onLeft(Entity sender);
	void onInteractedWith();
}

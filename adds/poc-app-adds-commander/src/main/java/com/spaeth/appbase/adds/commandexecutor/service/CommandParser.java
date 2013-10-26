package com.spaeth.appbase.adds.commandexecutor.service;

/**
 * Parses a provided string into a command.
 * 
 * @author "Francisco Spaeth (francisco.spaeth@gmail.com)"
 * 
 */
public interface CommandParser {

	Command parse(String command);

}

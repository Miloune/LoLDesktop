/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.loldesktop.objects;

import java.util.ArrayList;

/**
 *
 * @author ubuntudev
 */
public class LevelTip {
    private ArrayList<String> effect;
	private ArrayList<String> label;

	/**
	 * Returns effect.
	 *
	 * @return ArrayList<String> effect.
	 */
	public ArrayList<String> getEffect()
	{
		return effect;
	}

	/**
	 * Sets effect.
	 *
	 * @param effect effect.
	 */
	public void setEffect(ArrayList<String> effect)
	{
		this.effect = effect;
	}

	/**
	 * Returns label.
	 *
	 * @return ArrayList<String> label.
	 */
	public ArrayList<String> getLabel()
	{
		return label;
	}

	/**
	 * Sets label.
	 *
	 * @param label label.
	 */
	public void setLabel(ArrayList<String> label)
	{
		this.label = label;
	}
}

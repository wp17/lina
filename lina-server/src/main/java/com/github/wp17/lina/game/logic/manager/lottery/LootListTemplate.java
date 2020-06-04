package com.github.wp17.lina.game.logic.manager.lottery;

public class LootListTemplate {
  /**
   * 掉落ID
   */
  protected int Id;
  /**
   * 展示组开关
   */
  protected int ShowSW;
  /**
   * 展示次数
   */
  protected int ShowTime;
  /**
   * 展示奖励s
   */
  protected String[] ShowLoot;
  /**
   * 固定组开关
   */
  protected int FixedSW;
  /**
   * 固定次数
   */
  protected int FixedTime;
  /**
   * 固定奖励s
   */
  protected String[] FixedLoot;
  /**
   * 权重组开关
   */
  protected int WeightSW;
  /**
   * 权重次数
   */
  protected int WeightTime;
  /**
   * 权重奖励s
   */
  protected String[] WeightLoot;
  /**
   * 随机组开关
   */
  protected int RandomSW;
  /**
   * 随机组次数
   */
  protected int RandomTime;
  /**
   * 随机组奖励
   */
  protected String[] RandomLoot;
}

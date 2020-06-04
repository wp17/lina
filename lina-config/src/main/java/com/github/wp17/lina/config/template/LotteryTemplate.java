package com.github.wp17.lina.config.template;

import com.github.wp17.lina.config.common.ConfigMetadata;
import lombok.Data;
/**
 * auto generate, do not modify
 */
@Data
@ConfigMetadata(path = "Lottery.xlsx")
public class LotteryTemplate {
  /**
   * 掉落ID
   */
  private int Id;
  /**
   * 展示组开关
   */
  private int ShowSW;
  /**
   * 展示次数
   */
  private int ShowTime;
  /**
   * 展示奖励s
   */
  private String[] ShowLoot;
  /**
   * 固定组开关
   */
  private int FixedSW;
  /**
   * 固定次数
   */
  private int FixedTime;
  /**
   * 固定奖励s
   */
  private String[] FixedLoot;
  /**
   * 权重组开关
   */
  private int WeightSW;
  /**
   * 权重次数
   */
  private int WeightTime;
  /**
   * 权重奖励s
   */
  private String[] WeightLoot;
  /**
   * 随机组开关
   */
  private int RandomSW;
  /**
   * 随机组次数
   */
  private int RandomTime;
  /**
   * 随机组奖励
   */
  private String[] RandomLoot;
}

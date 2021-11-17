package com.wykblog.blog.utils;

public class Entry<K, V> {
  private K key;
  
  private V value;
  
  public Entry() {}
  
  public Entry(K key, V value) {
    this.key = key;
    this.value = value;
  }
  
  public void setKey(K key) {
    this.key = key;
  }
  
  public void setValue(V value) {
    this.value = value;
  }
  
  public K getKey() {
    return this.key;
  }
  
  public V getValue() {
    return this.value;
  }
  
  public String toString() {
    return String.valueOf(String.valueOf(this.key)) + ": " + String.valueOf(this.value);
  }
  
  public boolean isNullValue() {
    return (this.key == null);
  }
}
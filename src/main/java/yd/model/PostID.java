package yd.model;

import javax.validation.constraints.NotNull;

public final class PostID {
  public final String author;
  public final long timestamp;

  public PostID(@NotNull String author, long timestamp) {
    this.author = author;
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PostID postID = (PostID) o;

    if (timestamp != postID.timestamp) return false;
    return author.equals(postID.author);

  }

  @Override
  public int hashCode() {
    int result = author.hashCode();
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    return result;
  }
}

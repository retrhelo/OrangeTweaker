package net.orangetweaker.zen.representation;

/**
 * Representation is the frontend data structure for ZenScripts. It usually
 * represents various objects that the develop can add to the game via scripts.
 *
 * IRepresentation puts defines the general behavior that a Representation
 * object should have.
 */
public interface IRepresentation {
  /**
   * Get the unlocalized name for the object. An unlocalized name is the
   * internal name used in the game to identify one object.
   */
  String getUnlocalizedName();

  /**
   * Register this presentation to the game. It's only after register() that
   * the corresponding custom stuff (Item, CreativeTab, etc.) appears in the
   * game play.
   */
  void register();
}

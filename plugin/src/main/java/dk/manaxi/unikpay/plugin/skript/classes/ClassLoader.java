package dk.manaxi.unikpay.plugin.skript.classes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import org.jetbrains.annotations.NotNull;

public class ClassLoader {

    static {
        Classes.registerClass(new ClassInfo<>(AcceptId.class, "id")
                .defaultExpression(new EventValueExpression<>(AcceptId.class))
                .user("id")
                .name("id")
                .description("betalings id")
                .parser(new Parser<AcceptId>() {
                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }
                    @Override
                    public AcceptId parse(@NotNull String arg0, @NotNull ParseContext arg1) {
                        return null;
                    }

                    @NotNull
                    @Override
                    public String toString(AcceptId arg0, int arg1) {
                        return arg0.toString();
                    }

                    @NotNull
                    @Override
                    public String toVariableNameString(AcceptId arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(Pakke.class, "pakke")
                .defaultExpression(new EventValueExpression<>(Pakke.class))
                .user("pakker?")
                .name("pakke")
                .description("pakke")
                .parser(new Parser<Pakke>() {
                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public Pakke parse(@NotNull String arg0, @NotNull ParseContext arg1) {
                        return null;
                    }

                    @NotNull
                    @Override
                    public String toString(Pakke arg0, int arg1) {
                        return arg0.toString();
                    }

                    @NotNull
                    @Override
                    public String toVariableNameString(Pakke arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(Subscription.class, "subscription")
                .defaultExpression(new EventValueExpression<>(Subscription.class))
                .user("subscription?")
                .name("subscription")
                .description("subscription")
                .parser(new Parser<Subscription>() {
                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }
                    @Override
                    public Subscription parse(@NotNull String arg0, @NotNull ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public @NotNull String toString(Subscription arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public @NotNull String toVariableNameString(Subscription arg0) {
                        return arg0.toString();
                    }

                }));
    }
}

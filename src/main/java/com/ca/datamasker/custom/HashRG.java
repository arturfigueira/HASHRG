package com.ca.datamasker.custom;

import com.grid_tools.products.datamasker.Datamasker;
import com.grid_tools.products.datamasker.IMaskFunction;
import com.grid_tools.products.datamasker.randfunctions;

public class HashRG implements IMaskFunction {

    @Override
    public Object mask(Object... objects) {
        final String inputValue = (String) objects[0];

        String maskedValue = inputValue;
        if(inputValue !=  null && inputValue.isEmpty()){
            try{
                maskedValue = hashIt(inputValue);
            }catch (IllegalArgumentException e){
                Datamasker.processOutputs(Datamasker.formatMessage("m0345-hasherr", new String[] { "HASH" }));
                Datamasker.processErrors(Datamasker.formatMessage("m0345-hasherr", new String[] { "HASH" }));
                Datamasker.processOutputs(Datamasker.formatMessage("m0155-DBValue", new String[] { inputValue }));
                System.exit(1);
            }
        }

        return maskedValue;
    }

    protected String hashIt(final String originalRG){
        final String rgWithoutDV = originalRG.substring(0, originalRG.length() - 1);

        final String cleanedRG = originalRG.replaceAll("\\D", "");
        if(!RGValidator.isValid(cleanedRG)){
            throw new IllegalArgumentException("This RG is invalid and cant be hashed");
        }

        final String hashedRG = randfunctions.formatHash(rgWithoutDV);

        final RegistroGeral registroGeral = new RegistroGeral(hashedRG);
        return registroGeral.toString();
    }
}

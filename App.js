import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// Importa as telas criadas no seu diretório src/screens
import HomeScreen from './src/screens/HomeScreen';
import FormScreen from './src/screens/FormScreen';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{ title: 'Gestão de Clientes da Granja' }}
        />
        <Stack.Screen
          name="Form"
          component={FormScreen}
          // O título muda dinamicamente com base se está editando ou criando
          options={({ route }) => ({
            title: route.params?.cliente ? 'Editar Cliente' : 'Novo Cliente',
          })}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
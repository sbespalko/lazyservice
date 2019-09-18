export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  house?: string;
  clientId?: number;
}

export const defaultValue: Readonly<IAddress> = {};

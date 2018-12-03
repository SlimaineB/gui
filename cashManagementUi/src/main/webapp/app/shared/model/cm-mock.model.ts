export interface ICmMock {
    id?: number;
    mockId?: number;
    mockServiceName?: number;
    mockSearchKey?: string;
    mockSearchValue?: string;
    mockedBody?: string;
    mockedHttpCode?: string;
    mockedTime?: number;
}

export class CmMock implements ICmMock {
    constructor(
        public id?: number,
        public mockId?: number,
        public mockServiceName?: number,
        public mockSearchKey?: string,
        public mockSearchValue?: string,
        public mockedBody?: string,
        public mockedHttpCode?: string,
        public mockedTime?: number
    ) {}
}

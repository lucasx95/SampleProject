import { BaseEntity } from './../../shared';

export class Sample implements BaseEntity {
    constructor(
        public id?: number,
        public sampleName?: string,
        public sampleSize?: number,
        public sampleOneId?: number,
        public sampleManies?: BaseEntity[],
    ) {
    }
}

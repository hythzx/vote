import { BaseEntity } from './../../shared';

export class VoteItem implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public voteCount?: number,
        public vote?: BaseEntity,
    ) {
    }
}
